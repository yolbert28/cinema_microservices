import { BaseEntity } from "src/common/entities/base.entity";
import { Movie } from "src/movie/entities/movie.entity";
import { Column, Entity, ManyToMany } from "typeorm";

@Entity()
export class Genre extends BaseEntity {

    @Column({ type: 'varchar', unique: true })
    name: string;

    @ManyToMany(() => Movie, (movie) => movie.genres)
    movies: Movie[];
}
