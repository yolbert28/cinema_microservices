import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { Showtime } from './entities/showtime.entity';
import { CreateShowtimeDto } from './dto/create-showtime.dto';
import { UpdateShowtimeDto } from './dto/update-showtime.dto';

@Injectable()
export class ShowtimeService {

  constructor(
    @InjectRepository(Showtime)
    private showtimeRepository: Repository<Showtime>
  ) {}
  async create(createShowtimeDto: CreateShowtimeDto) {
    const newEntity = this.showtimeRepository.create(createShowtimeDto);
    return await this.showtimeRepository.save(newEntity);
  }

  async findAll(): Promise<Showtime[]> {
    return await this.showtimeRepository.find();
  }

  async findOne(id: string): Promise<Showtime | null> {
    return await this.showtimeRepository.findOne({ where: { id } as any });
  } showtime`;
  }

  async update(id: string, updateShowtimeDto: UpdateShowtimeDto) {
    await this.showtimeRepository.update(id, updateShowtimeDto as any);
    return this.findOne(id);
  } showtime`;
  }

  async remove(id: string): Promise<void> {
    await this.showtimeRepository.delete(id);
  } showtime`;
  }
}
