import { BaseEntity } from "src/common/entities/base.entity";
import { Showtime } from "src/showtime/entities/showtime.entity";
import { Column, Entity, OneToMany } from "typeorm";

@Entity()
export class Format extends BaseEntity {

    @Column({ type: 'varchar', unique: true })
    name: string;

    @OneToMany(() => Showtime, (showtime) => showtime.format)
    showtimes: Showtime[];

}
