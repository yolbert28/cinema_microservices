import { Module } from '@nestjs/common';
import { MemberRoleService } from './member-role.service';
import { MemberRoleController } from './member-role.controller';
import { TypeOrmModule } from '@nestjs/typeorm';
import { MemberRole } from './entities/member-role.entity';

@Module({
  imports: [
    TypeOrmModule.forFeature([MemberRole]),
  ],
  controllers: [MemberRoleController],
  providers: [MemberRoleService],
})
export class MemberRoleModule { }
