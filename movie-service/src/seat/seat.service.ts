import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { Seat } from './entities/seat.entity';
import { CreateSeatDto } from './dto/create-seat.dto';
import { UpdateSeatDto } from './dto/update-seat.dto';

@Injectable()
export class SeatService {

  constructor(
    @InjectRepository(Seat)
    private seatRepository: Repository<Seat>
  ) {}
  async create(createSeatDto: CreateSeatDto) {
    const newEntity = this.seatRepository.create(createSeatDto);
    return await this.seatRepository.save(newEntity);
  }

  async findAll(): Promise<Seat[]> {
    return await this.seatRepository.find();
  }

  async findOne(id: string): Promise<Seat | null> {
    return await this.seatRepository.findOne({ where: { id } as any });
  } seat`;
  }

  async update(id: string, updateSeatDto: UpdateSeatDto) {
    await this.seatRepository.update(id, updateSeatDto as any);
    return this.findOne(id);
  } seat`;
  }

  async remove(id: string): Promise<void> {
    await this.seatRepository.delete(id);
  } seat`;
  }
}
