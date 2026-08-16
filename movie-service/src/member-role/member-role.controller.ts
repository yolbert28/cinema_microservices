import { Controller, Get, Post, Body, Patch, Param, Delete } from '@nestjs/common';
import { MemberRoleService } from './member-role.service';
import { CreateMemberRoleDto } from './dto/create-member-role.dto';
import { UpdateMemberRoleDto } from './dto/update-member-role.dto';

@Controller('member-role')
export class MemberRoleController {
  constructor(private readonly memberRoleService: MemberRoleService) {}

  @Post()
  create(@Body() createMemberRoleDto: CreateMemberRoleDto) {
    return this.memberRoleService.create(createMemberRoleDto);
  }

  @Get()
  findAll() {
    return this.memberRoleService.findAll();
  }

  @Get(':id')
  findOne(@Param('id') id: string) {
    return this.memberRoleService.findOne(+id);
  }

  @Patch(':id')
  update(@Param('id') id: string, @Body() updateMemberRoleDto: UpdateMemberRoleDto) {
    return this.memberRoleService.update(+id, updateMemberRoleDto);
  }

  @Delete(':id')
  remove(@Param('id') id: string) {
    return this.memberRoleService.remove(+id);
  }
}
