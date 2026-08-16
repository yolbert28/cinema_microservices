import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { Movie } from './entities/movie.entity';
import { CreateMovieDto } from './dto/create-movie.dto';
import { UpdateMovieDto } from './dto/update-movie.dto';

@Injectable()
export class MovieService {

  constructor(
    @InjectRepository(Movie)
    private movieRepository: Repository<Movie>
  ) {}
  async create(createMovieDto: CreateMovieDto) {
    const newEntity = this.movieRepository.create(createMovieDto);
    return await this.movieRepository.save(newEntity);
  }

  async findAll(): Promise<Movie[]> {
    return await this.movieRepository.find();
  }

  async findOne(id: string): Promise<Movie | null> {
    return await this.movieRepository.findOne({ where: { id } as any });
  } movie`;
  }

  async update(id: string, updateMovieDto: UpdateMovieDto) {
    await this.movieRepository.update(id, updateMovieDto as any);
    return this.findOne(id);
  } movie`;
  }

  async remove(id: string): Promise<void> {
    await this.movieRepository.delete(id);
  } movie`;
  }
}
