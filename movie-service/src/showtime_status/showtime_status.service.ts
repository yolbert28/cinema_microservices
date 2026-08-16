import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { ShowtimeStatus } from './entities/showtime_status.entity';
import { CreateShowtimeStatusDto } from './dto/create-showtime_status.dto';
import { UpdateShowtimeStatusDto } from './dto/update-showtime_status.dto';

@Injectable()
export class ShowtimeStatusService {

  constructor(
    @InjectRepository(ShowtimeStatus)
    private showtimeStatusRepository: Repository<ShowtimeStatus>
  ) {}
  async create(createShowtimeStatusDto: CreateShowtimeStatusDto) {
    const newEntity = this.showtimeStatusRepository.create(createShowtimeStatusDto);
    return await this.showtimeStatusRepository.save(newEntity);
  }

  async findAll(): Promise<ShowtimeStatus[]> {
    return await this.showtimeStatusRepository.find();
  }

  async findOne(id: string): Promise<ShowtimeStatus | null> {
    return await this.showtimeStatusRepository.findOne({ where: { id } as any });
  } showtimeStatus`;
  }

  async update(id: string, updateShowtimeStatusDto: UpdateShowtimeStatusDto) {
    await this.showtimeStatusRepository.update(id, updateShowtimeStatusDto as any);
    return this.findOne(id);
  } showtimeStatus`;
  }

  async remove(id: string): Promise<void> {
    await this.showtimeStatusRepository.delete(id);
  } showtimeStatus`;
  }
}
