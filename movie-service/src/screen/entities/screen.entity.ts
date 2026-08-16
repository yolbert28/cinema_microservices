import { Cinema } from "src/cinema/entities/cinema.entity";
import { BaseEntity } from "src/common/entities/base.entity";
import { RoomType } from "src/room-type/entities/room-type.entity";
import { Seat } from "src/seat/entities/seat.entity";
import { Showtime } from "src/showtime/entities/showtime.entity";
import { Column, Entity, ManyToOne, OneToMany, Unique } from "typeorm";

@Entity()
@Unique('UQ_cinema_name', ['cinema', 'name'])
export class Screen extends BaseEntity {

    @Column({ type: 'varchar' })
    name: string;

    @ManyToOne(() => Cinema, (cinema) => cinema.screens)
    cinema: Cinema;

    @ManyToOne(() => RoomType, (roomType) => roomType.screens)
    roomType: RoomType;

    @OneToMany(() => Seat, (seat) => seat.screen)
    seats: Seat[];

    @OneToMany(() => Showtime, (showtime) => showtime.screen)
    showtimes: Showtime[];

} 