import { Test, TestingModule } from '@nestjs/testing';
import { ShowtimeStatusController } from './showtime_status.controller';
import { ShowtimeStatusService } from './showtime_status.service';

describe('ShowtimeStatusController', () => {
  let controller: ShowtimeStatusController;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      controllers: [ShowtimeStatusController],
      providers: [ShowtimeStatusService],
    }).compile();

    controller = module.get<ShowtimeStatusController>(ShowtimeStatusController);
  });

  it('should be defined', () => {
    expect(controller).toBeDefined();
  });
});
