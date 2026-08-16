import { BaseEntity } from "src/common/entities/base.entity";
import { Column, Entity, OneToMany } from "typeorm";
import { Cinema } from "../../cinema/entities/cinema.entity";

@Entity()
export class State extends BaseEntity {

    @Column({ type: 'varchar', unique: true })
    name: string;

    @OneToMany(() => Cinema, (cinema) => cinema.state)
    cinemas: Cinema[];

}