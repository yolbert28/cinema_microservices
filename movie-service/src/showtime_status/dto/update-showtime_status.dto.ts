import { PartialType } from '@nestjs/mapped-types';
import { CreateShowtimeStatusDto } from './create-showtime_status.dto';

export class UpdateShowtimeStatusDto extends PartialType(CreateShowtimeStatusDto) {}
