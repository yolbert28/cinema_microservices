import { Controller, Get, Post, Body, Patch, Param, Delete } from '@nestjs/common';
import { ShowtimeStatusService } from './showtime_status.service';
import { CreateShowtimeStatusDto } from './dto/create-showtime_status.dto';
import { UpdateShowtimeStatusDto } from './dto/update-showtime_status.dto';

@Controller('showtime-status')
export class ShowtimeStatusController {
  constructor(private readonly showtimeStatusService: ShowtimeStatusService) {}

  @Post()
  create(@Body() createShowtimeStatusDto: CreateShowtimeStatusDto) {
    return this.showtimeStatusService.create(createShowtimeStatusDto);
  }

  @Get()
  findAll() {
    return this.showtimeStatusService.findAll();
  }

  @Get(':id')
  findOne(@Param('id') id: string) {
    return this.showtimeStatusService.findOne(+id);
  }

  @Patch(':id')
  update(@Param('id') id: string, @Body() updateShowtimeStatusDto: UpdateShowtimeStatusDto) {
    return this.showtimeStatusService.update(+id, updateShowtimeStatusDto);
  }

  @Delete(':id')
  remove(@Param('id') id: string) {
    return this.showtimeStatusService.remove(+id);
  }
}
