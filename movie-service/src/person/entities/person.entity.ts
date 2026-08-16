import { BaseEntity } from "src/common/entities/base.entity";
import { CastMember } from "src/movie/entities/cast-member.entity";
import { Column, Entity, OneToMany } from "typeorm";

@Entity()
export class Person extends BaseEntity {

    @Column({ type: 'varchar' })
    name: string;

    @Column({ type: 'text' })
    image: string;


    @OneToMany(() => CastMember, (castMember) => castMember.movie)
    castMembers: CastMember;
}
