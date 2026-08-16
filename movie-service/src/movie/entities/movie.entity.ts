import { BaseEntity } from "src/common/entities/base.entity";
import { Genre } from "src/genre/entities/genre.entity";
import { Showtime } from "src/showtime/entities/showtime.entity";
import { Column, Entity, JoinTable, ManyToMany, OneToMany } from "typeorm";
import { CastMember } from "./cast-member.entity";

@Entity()
export class Movie extends BaseEntity {

    @Column({ type: 'varchar' })
    name: string;

    @Column({ type: 'text' })
    description: string;

    @Column({ type: 'text' })
    poster: string;

    @Column({ type: 'text' })
    trailer: string;

    @Column({ name: 'trailer_poster', type: 'text' })
    trailerPoster: string;

    @Column({ type: 'integer' })
    duration: number;

    @Column({ name: 'release_date', type: 'date' })
    releaseDate: Date;

    @ManyToMany(() => Genre, (genre) => genre.movies)
    @JoinTable({
        name: 'genre_movie',
        joinColumn: { name: 'movie_id', referencedColumnName: 'id' },
        inverseJoinColumn: { name: 'genre_id', referencedColumnName: 'id' },
    })
    genres: Genre[];

    @OneToMany(() => CastMember, (castMember) => castMember.movie)
    castMembers: CastMember;

    @OneToMany(() => Showtime, (showtime) => showtime.movie)
    showtimes: Showtime[];

}
