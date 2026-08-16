import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { State } from './entities/state.entity';
import { CreateStateDto } from './dto/create-state.dto';
import { UpdateStateDto } from './dto/update-state.dto';

@Injectable()
export class StateService {

  constructor(
    @InjectRepository(State)
    private stateRepository: Repository<State>
  ) {}
  async create(createStateDto: CreateStateDto) {
    const newEntity = this.stateRepository.create(createStateDto);
    return await this.stateRepository.save(newEntity);
  }

  async findAll(): Promise<State[]> {
    return await this.stateRepository.find();
  }

  async findOne(id: string): Promise<State | null> {
    return await this.stateRepository.findOne({ where: { id } as any });
  } state`;
  }

  async update(id: string, updateStateDto: UpdateStateDto) {
    await this.stateRepository.update(id, updateStateDto as any);
    return this.findOne(id);
  } state`;
  }

  async remove(id: string): Promise<void> {
    await this.stateRepository.delete(id);
  } state`;
  }
}
