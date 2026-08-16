import { BaseEntity } from "src/common/entities/base.entity";
import { Format } from "src/format/entities/format.entity";
import { Language } from "src/language/entities/language.entity";
import { Movie } from "src/movie/entities/movie.entity";
import { Screen } from "src/screen/entities/screen.entity";
import { Column, Entity, ManyToOne } from "typeorm";
import { ShowtimeStatus } from "src/showtime_status/entities/showtime_status.entity";

@Entity()
export class Showtime extends BaseEntity {

    @Column({ name: 'start_time', type: 'timestamptz' })
    startTime: Date;

    @Column({ name: 'end_time', type: 'timestamptz' })
    endTime: Date;

    @Column({ name: 'cleanup_minutes', type: 'integer' })
    cleanupMinutes: number;

    @ManyToOne(() => Movie, (movie) => movie.showtimes)
    movie: Movie;

    @ManyToOne(() => Screen, (screen) => screen.showtimes)
    screen: Screen;

    @ManyToOne(() => Language, (language) => language.showtimes)
    language: Language;

    @ManyToOne(() => Format, (format) => format.showtimes)
    format: Format;

    @ManyToOne(() => ShowtimeStatus, (status) => status.showtimes)
    status: ShowtimeStatus;

}
