import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { Genre } from './entities/genre.entity';
import { CreateGenreDto } from './dto/create-genre.dto';
import { UpdateGenreDto } from './dto/update-genre.dto';

@Injectable()
export class GenreService {

  constructor(
    @InjectRepository(Genre)
    private genreRepository: Repository<Genre>
  ) {}
  async create(createGenreDto: CreateGenreDto) {
    const newEntity = this.genreRepository.create(createGenreDto);
    return await this.genreRepository.save(newEntity);
  }

  async findAll(): Promise<Genre[]> {
    return await this.genreRepository.find();
  }

  async findOne(id: string): Promise<Genre | null> {
    return await this.genreRepository.findOne({ where: { id } as any });
  } genre`;
  }

  async update(id: string, updateGenreDto: UpdateGenreDto) {
    await this.genreRepository.update(id, updateGenreDto as any);
    return this.findOne(id);
  } genre`;
  }

  async remove(id: string): Promise<void> {
    await this.genreRepository.delete(id);
  } genre`;
  }
}
