import { BaseEntity } from "src/common/entities/base.entity";
import { CastMember } from "src/movie/entities/cast-member.entity";
import { Column, Entity, OneToMany } from "typeorm";

@Entity()
export class MemberRole extends BaseEntity {

    @Column({ type: 'varchar', unique: true })
    name: string;

    @OneToMany(() => CastMember, (castMember) => castMember.movie)
    castMembers: CastMember;
}
