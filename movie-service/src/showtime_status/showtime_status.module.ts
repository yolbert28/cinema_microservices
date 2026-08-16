import { Module } from '@nestjs/common';
import { ShowtimeStatusService } from './showtime_status.service';
import { ShowtimeStatusController } from './showtime_status.controller';
import { TypeOrmModule } from '@nestjs/typeorm';
import { ShowtimeStatus } from './entities/showtime_status.entity';

@Module({
  imports: [
    TypeOrmModule.forFeature([ShowtimeStatus])
  ],
  controllers: [ShowtimeStatusController],
  providers: [ShowtimeStatusService],
})
export class ShowtimeStatusModule { }
