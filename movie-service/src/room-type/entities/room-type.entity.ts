import { BaseEntity } from "src/common/entities/base.entity";
import { Screen } from "src/screen/entities/screen.entity";
import { Column, Entity, OneToMany } from "typeorm";

@Entity()
export class RoomType extends BaseEntity {

    @Column({ type: 'varchar', unique: true })
    name: string;

    @OneToMany(() => Screen, (screen) => screen.cinema)
    screens: Screen[];

} 