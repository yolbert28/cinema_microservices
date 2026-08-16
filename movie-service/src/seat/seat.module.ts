import { Module } from '@nestjs/common';
import { SeatService } from './seat.service';
import { SeatController } from './seat.controller';
import { TypeOrmModule } from '@nestjs/typeorm';
import { Seat } from './entities/seat.entity';
import { SeatStatus } from './entities/seat-status.entity';

@Module({
  imports: [
    TypeOrmModule.forFeature([Seat, SeatStatus])
  ],
  controllers: [SeatController],
  providers: [SeatService],
})
export class SeatModule { }
