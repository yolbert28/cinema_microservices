import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { RoomType } from './entities/room-type.entity';
import { CreateRoomTypeDto } from './dto/create-room-type.dto';
import { UpdateRoomTypeDto } from './dto/update-room-type.dto';

@Injectable()
export class RoomTypeService {

  constructor(
    @InjectRepository(RoomType)
    private roomTypeRepository: Repository<RoomType>
  ) {}
  async create(createRoomTypeDto: CreateRoomTypeDto) {
    const newEntity = this.roomTypeRepository.create(createRoomTypeDto);
    return await this.roomTypeRepository.save(newEntity);
  }

  async findAll(): Promise<RoomType[]> {
    return await this.roomTypeRepository.find();
  }

  async findOne(id: string): Promise<RoomType | null> {
    return await this.roomTypeRepository.findOne({ where: { id } as any });
  } roomType`;
  }

  async update(id: string, updateRoomTypeDto: UpdateRoomTypeDto) {
    await this.roomTypeRepository.update(id, updateRoomTypeDto as any);
    return this.findOne(id);
  } roomType`;
  }

  async remove(id: string): Promise<void> {
    await this.roomTypeRepository.delete(id);
  } roomType`;
  }
}
