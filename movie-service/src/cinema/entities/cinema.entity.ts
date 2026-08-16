import { BaseEntity } from "src/common/entities/base.entity";
import { Column, Entity, ManyToOne, OneToMany } from "typeorm";
import { State } from "../../state/entities/state.entity";
import { Screen } from "src/screen/entities/screen.entity";

@Entity()
export class Cinema extends BaseEntity {

    @Column({ type: 'varchar', unique: true })
    name: string;

    @Column({ type: 'text' })
    address: string;

    @Column({ type: 'text' })
    image: string;

    @ManyToOne(() => State, (state) => state.cinemas)
    state: State;

    @OneToMany(() => Screen, (screen) => screen.cinema)
    screens: Screen[];

}  