import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { Cinema } from './entities/cinema.entity';
import { CreateCinemaDto } from './dto/create-cinema.dto';
import { UpdateCinemaDto } from './dto/update-cinema.dto';

@Injectable()
export class CinemaService {

  constructor(
    @InjectRepository(Cinema)
    private cinemaRepository: Repository<Cinema>
  ) {}
  async create(createCinemaDto: CreateCinemaDto) {
    const newEntity = this.cinemaRepository.create(createCinemaDto);
    return await this.cinemaRepository.save(newEntity);
  }

  async findAll(): Promise<Cinema[]> {
    return await this.cinemaRepository.find();
  }

  async findOne(id: string): Promise<Cinema | null> {
    return await this.cinemaRepository.findOne({ where: { id } as any });
  } cinema`;
  }

  async update(id: string, updateCinemaDto: UpdateCinemaDto) {
    await this.cinemaRepository.update(id, updateCinemaDto as any);
    return this.findOne(id);
  } cinema`;
  }

  async remove(id: string): Promise<void> {
    await this.cinemaRepository.delete(id);
  } cinema`;
  }
}
