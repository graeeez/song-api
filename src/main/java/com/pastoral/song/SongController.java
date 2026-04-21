package com.pastoral.song;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "https://song-ui-096r.onrender.com")
@RequestMapping("/pastoral/songs")
public class SongController {

    private final SongRepository songRepository;

    @Autowired
    public SongController(SongRepository songRepository) {
        this.songRepository = songRepository;
    }


    @GetMapping
    public List<Song> getAllSongs() {
        return songRepository.findAll();
    }


    @PostMapping
    public Song addSong(@RequestBody Song song) {

        song.setId(null);
        return songRepository.save(song);
    }


    @GetMapping("/{id}")
    public Song getSongById(@PathVariable Long id) {
        Optional<Song> found = songRepository.findById(id);
        return found.orElse(null);
    }


    @DeleteMapping("/{id}")
    public String deleteSong(@PathVariable Long id) {
        songRepository.deleteById(id);
        return "Song with ID " + id + " deleted.";
    }


    @PutMapping("/{id}")
    public Song updateSong(@PathVariable Long id, @RequestBody Song song) {
        Song existing = songRepository.findById(id).orElseGet(Song::new);

        existing.setId(id);
        existing.setTitle(song.getTitle());
        existing.setArtist(song.getArtist());
        existing.setAlbum(song.getAlbum());
        existing.setGenre(song.getGenre());
        existing.setUrl(song.getUrl());

        return songRepository.save(existing);
    }


    @GetMapping("/search/{term}")
    public List<Song> searchSongs(@PathVariable String term) {

        return songRepository
                .findByTitleContainingIgnoreCaseOrArtistContainingIgnoreCaseOrAlbumContainingIgnoreCaseOrGenreContainingIgnoreCase(
                        term, term, term, term
                );
    }
}