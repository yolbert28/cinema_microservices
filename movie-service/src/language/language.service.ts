import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { Language } from './entities/language.entity';
import { CreateLanguageDto } from './dto/create-language.dto';
import { UpdateLanguageDto } from './dto/update-language.dto';

@Injectable()
export class LanguageService {

  constructor(
    @InjectRepository(Language)
    private languageRepository: Repository<Language>
  ) {}
  async create(createLanguageDto: CreateLanguageDto) {
    const newEntity = this.languageRepository.create(createLanguageDto);
    return await this.languageRepository.save(newEntity);
  }

  async findAll(): Promise<Language[]> {
    return await this.languageRepository.find();
  }

  async findOne(id: string): Promise<Language | null> {
    return await this.languageRepository.findOne({ where: { id } as any });
  } language`;
  }

  async update(id: string, updateLanguageDto: UpdateLanguageDto) {
    await this.languageRepository.update(id, updateLanguageDto as any);
    return this.findOne(id);
  } language`;
  }

  async remove(id: string): Promise<void> {
    await this.languageRepository.delete(id);
  } language`;
  }
}
