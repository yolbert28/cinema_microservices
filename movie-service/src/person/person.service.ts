import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { Person } from './entities/person.entity';
import { CreatePersonDto } from './dto/create-person.dto';
import { UpdatePersonDto } from './dto/update-person.dto';

@Injectable()
export class PersonService {

  constructor(
    @InjectRepository(Person)
    private personRepository: Repository<Person>
  ) {}
  async create(createPersonDto: CreatePersonDto) {
    const newEntity = this.personRepository.create(createPersonDto);
    return await this.personRepository.save(newEntity);
  }

  async findAll(): Promise<Person[]> {
    return await this.personRepository.find();
  }

  async findOne(id: string): Promise<Person | null> {
    return await this.personRepository.findOne({ where: { id } as any });
  } person`;
  }

  async update(id: string, updatePersonDto: UpdatePersonDto) {
    await this.personRepository.update(id, updatePersonDto as any);
    return this.findOne(id);
  } person`;
  }

  async remove(id: string): Promise<void> {
    await this.personRepository.delete(id);
  } person`;
  }
}
