import { Person } from 'src/person/entities/person.entity';
import {
    Entity,
    PrimaryGeneratedColumn,
    Column,
    ManyToOne,
    JoinColumn,
} from 'typeorm';
import { Movie } from './movie.entity';
import { MemberRole } from 'src/member-role/entities/member-role.entity';

@Entity('cast_member')
export class CastMember {
    @PrimaryGeneratedColumn('uuid')
    id: string;

    @ManyToOne(() => Person, (person) => person.castMembers, {
        nullable: false,
        onDelete: 'CASCADE',
    })
    @JoinColumn({ name: 'person_id' })
    person: Person;

    @ManyToOne(() => Movie, (movie) => movie.castMembers, {
        nullable: false,
        onDelete: 'CASCADE',
    })
    @JoinColumn({ name: 'movie_id' })
    movie: Movie;

    @ManyToOne(() => MemberRole, (role) => role.castMembers, {
        nullable: false,
        onDelete: 'CASCADE',
    })
    @JoinColumn({ name: 'role_id' })
    role: MemberRole;
}