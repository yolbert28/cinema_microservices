import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { MemberRole } from './entities/member-role.entity';
import { CreateMemberRoleDto } from './dto/create-member-role.dto';
import { UpdateMemberRoleDto } from './dto/update-member-role.dto';

@Injectable()
export class MemberRoleService {

  constructor(
    @InjectRepository(MemberRole)
    private memberRoleRepository: Repository<MemberRole>
  ) {}
  async create(createMemberRoleDto: CreateMemberRoleDto) {
    const newEntity = this.memberRoleRepository.create(createMemberRoleDto);
    return await this.memberRoleRepository.save(newEntity);
  }

  async findAll(): Promise<MemberRole[]> {
    return await this.memberRoleRepository.find();
  }

  async findOne(id: string): Promise<MemberRole | null> {
    return await this.memberRoleRepository.findOne({ where: { id } as any });
  } memberRole`;
  }

  async update(id: string, updateMemberRoleDto: UpdateMemberRoleDto) {
    await this.memberRoleRepository.update(id, updateMemberRoleDto as any);
    return this.findOne(id);
  } memberRole`;
  }

  async remove(id: string): Promise<void> {
    await this.memberRoleRepository.delete(id);
  } memberRole`;
  }
}
