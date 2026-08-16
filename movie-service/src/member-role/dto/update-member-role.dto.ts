import { PartialType } from '@nestjs/mapped-types';
import { CreateMemberRoleDto } from './create-member-role.dto';

export class UpdateMemberRoleDto extends PartialType(CreateMemberRoleDto) {}
