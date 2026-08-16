const fs = require('fs');
const path = require('path');

const srcDir = path.join(__dirname, 'src');

function capitalize(s) {
    return s.charAt(0).toUpperCase() + s.slice(1);
}

function camelCase(s) {
    return s.replace(/_([a-z])/g, function (g) { return g[1].toUpperCase(); })
            .replace(/-([a-z])/g, function (g) { return g[1].toUpperCase(); });
}

function pascalCase(s) {
    const camel = camelCase(s);
    return capitalize(camel);
}

function findServiceFiles(dir, fileList = []) {
    const files = fs.readdirSync(dir);
    for (const file of files) {
        const filePath = path.join(dir, file);
        if (fs.statSync(filePath).isDirectory()) {
            findServiceFiles(filePath, fileList);
        } else if (file.endsWith('.service.ts')) {
            fileList.push(filePath);
        }
    }
    return fileList;
}

const serviceFiles = findServiceFiles(srcDir);

for (const file of serviceFiles) {
    const filename = path.basename(file);
    const entityBaseName = filename.replace('.service.ts', '');
    const entityName = pascalCase(entityBaseName); // e.g. showtime_status -> ShowtimeStatus
    const camelEntityName = camelCase(entityBaseName);
    const varName = camelEntityName + 'Repository';

    let content = fs.readFileSync(file, 'utf8');

    // Skip if already has InjectRepository
    if (content.includes('@InjectRepository')) {
        console.log(`Skipping ${filename}`);
        continue;
    }

    // Add imports
    const importRegex = /import\s+{.*}\s+from\s+'@nestjs\/common';/;
    content = content.replace(importRegex, `import { Injectable } from '@nestjs/common';\nimport { InjectRepository } from '@nestjs/typeorm';\nimport { Repository } from 'typeorm';\nimport { ${entityName} } from './entities/${entityBaseName}.entity';`);

    // Inject constructor
    const classRegex = new RegExp(`export class ${entityName}Service {`);
    content = content.replace(classRegex, `export class ${entityName}Service {\n\n  constructor(\n    @InjectRepository(${entityName})\n    private ${varName}: Repository<${entityName}>\n  ) {}`);

    // Update create
    const createRegex = new RegExp(`create\\(create${entityName}Dto: Create${entityName}Dto\\) {[^}]*}`);
    content = content.replace(createRegex, `async create(create${entityName}Dto: Create${entityName}Dto) {\n    const newEntity = this.${varName}.create(create${entityName}Dto);\n    return await this.${varName}.save(newEntity);\n  }`);

    // Update findAll
    const findAllRegex = /findAll\(\) {[^}]*}/;
    content = content.replace(findAllRegex, `async findAll(): Promise<${entityName}[]> {\n    return await this.${varName}.find();\n  }`);

    // Update findOne
    const findOneRegex = /findOne\(id: number\) {[^}]*}/;
    // Assuming UUID which means string id, so we should change id type to string if it's currently number. The entities use string id (uuid).
    // Let's replace the argument type as well
    const findOneArgRegex = /findOne\(id: [a-zA-Z]+\) {[^}]*}/;
    content = content.replace(findOneArgRegex, `async findOne(id: string): Promise<${entityName} | null> {\n    return await this.${varName}.findOne({ where: { id } as any });\n  }`);

    // Update update
    const updateArgRegex = new RegExp(`update\\(id: [a-zA-Z]+, update${entityName}Dto: Update${entityName}Dto\\) {[^}]*}`);
    content = content.replace(updateArgRegex, `async update(id: string, update${entityName}Dto: Update${entityName}Dto) {\n    await this.${varName}.update(id, update${entityName}Dto as any);\n    return this.findOne(id);\n  }`);

    // Update remove
    const removeArgRegex = /remove\(id: [a-zA-Z]+\) {[^}]*}/;
    content = content.replace(removeArgRegex, `async remove(id: string): Promise<void> {\n    await this.${varName}.delete(id);\n  }`);

    fs.writeFileSync(file, content);
    console.log(`Updated ${filename}`);
}

