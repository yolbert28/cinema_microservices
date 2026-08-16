import { BaseEntity } from "src/common/entities/base.entity";
import { Column, Entity, ManyToOne, Unique } from "typeorm";
import { SeatStatus } from "./seat-status.entity";
import { Screen } from "src/screen/entities/screen.entity";

@Entity()
@Unique('UQ_seat_screen_row_col', ['row', 'col', 'screen'])
export class Seat extends BaseEntity {

    @Column({ type: 'varchar' })
    row: string;

    @Column({ type: 'text' })
    col: number;

    @Column({ name: 'is_wheelchair_accessible', default: false })
    isWheelchairAccessible: boolean;

    @ManyToOne(() => SeatStatus, (status) => status.seats)
    status: SeatStatus;

    @ManyToOne(() => Screen, (screen) => screen.seats)
    screen: Screen;

} 