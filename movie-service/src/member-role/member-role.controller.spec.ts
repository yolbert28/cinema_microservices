import { Test, TestingModule } from '@nestjs/testing';
import { MemberRoleController } from './member-role.controller';
import { MemberRoleService } from './member-role.service';

describe('MemberRoleController', () => {
  let controller: MemberRoleController;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      controllers: [MemberRoleController],
      providers: [MemberRoleService],
    }).compile();

    controller = module.get<MemberRoleController>(MemberRoleController);
  });

  it('should be defined', () => {
    expect(controller).toBeDefined();
  });
});
