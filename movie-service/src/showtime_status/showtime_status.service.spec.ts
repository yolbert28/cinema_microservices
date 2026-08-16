import { Test, TestingModule } from '@nestjs/testing';
import { ShowtimeStatusService } from './showtime_status.service';

describe('ShowtimeStatusService', () => {
  let service: ShowtimeStatusService;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [ShowtimeStatusService],
    }).compile();

    service = module.get<ShowtimeStatusService>(ShowtimeStatusService);
  });

  it('should be defined', () => {
    expect(service).toBeDefined();
  });
});
