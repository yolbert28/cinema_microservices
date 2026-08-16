import { Test, TestingModule } from '@nestjs/testing';
import { MemberRoleService } from './member-role.service';

describe('MemberRoleService', () => {
  let service: MemberRoleService;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [MemberRoleService],
    }).compile();

    service = module.get<MemberRoleService>(MemberRoleService);
  });

  it('should be defined', () => {
    expect(service).toBeDefined();
  });
});
