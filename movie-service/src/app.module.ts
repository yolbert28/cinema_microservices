import { Module } from '@nestjs/common';
import { MovieModule } from './movie/movie.module';
import { CinemaModule } from './cinema/cinema.module';
import { ShowtimeModule } from './showtime/showtime.module';
import { ConfigModule, ConfigService } from '@nestjs/config';
import { TypeOrmModule, TypeOrmModuleOptions } from '@nestjs/typeorm';
import { StateModule } from './state/state.module';
import { RoomTypeModule } from './room-type/room-type.module';
import { ScreenModule } from './screen/screen.module';
import { SeatModule } from './seat/seat.module';
import { PersonModule } from './person/person.module';
import { GenreModule } from './genre/genre.module';
import { MemberRoleModule } from './member-role/member-role.module';
import { FormatModule } from './format/format.module';
import { ShowtimeStatusModule } from './showtime_status/showtime_status.module';
import { LanguageModule } from './language/language.module';
import databaseConfig from './config/database.config';

@Module({
  imports: [
    ConfigModule.forRoot({ isGlobal: true, load: [databaseConfig] }),
    TypeOrmModule.forRootAsync({
      inject: [ConfigService],
      useFactory: (config: ConfigService) => config.get<TypeOrmModuleOptions>('database')!,
    }), MovieModule, CinemaModule, ShowtimeModule, StateModule, SeatModule, ScreenModule, RoomTypeModule, PersonModule, GenreModule, MemberRoleModule, FormatModule, ShowtimeStatusModule, LanguageModule],
})
export class AppModule { }
