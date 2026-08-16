import { BaseEntity } from "src/common/entities/base.entity";
import { Showtime } from "src/showtime/entities/showtime.entity";
import { Column, Entity, OneToMany } from "typeorm";

@Entity()
export class Language extends BaseEntity {

    @Column({ type: 'varchar', unique: true })
    name: string;

    @Column({ type: 'varchar', unique: true })
    code: string;

    @OneToMany(() => Showtime, (showtime) => showtime.language)
    showtimes: Showtime[];

}
