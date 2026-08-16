import { BaseEntity } from "src/common/entities/base.entity";
import { Column, Entity, OneToMany } from "typeorm";
import { Seat } from "./seat.entity";

@Entity()
export class SeatStatus extends BaseEntity {

    @Column({ type: 'varchar', unique: true })
    name: string;

    @OneToMany(() => Seat, (seat) => seat.status)
    seats: Seat[];

} 