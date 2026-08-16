import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { Screen } from './entities/screen.entity';
import { CreateScreenDto } from './dto/create-screen.dto';
import { UpdateScreenDto } from './dto/update-screen.dto';

@Injectable()
export class ScreenService {

  constructor(
    @InjectRepository(Screen)
    private screenRepository: Repository<Screen>
  ) {}
  async create(createScreenDto: CreateScreenDto) {
    const newEntity = this.screenRepository.create(createScreenDto);
    return await this.screenRepository.save(newEntity);
  }

  async findAll(): Promise<Screen[]> {
    return await this.screenRepository.find();
  }

  async findOne(id: string): Promise<Screen | null> {
    return await this.screenRepository.findOne({ where: { id } as any });
  } screen`;
  }

  async update(id: string, updateScreenDto: UpdateScreenDto) {
    await this.screenRepository.update(id, updateScreenDto as any);
    return this.findOne(id);
  } screen`;
  }

  async remove(id: string): Promise<void> {
    await this.screenRepository.delete(id);
  } screen`;
  }
}
