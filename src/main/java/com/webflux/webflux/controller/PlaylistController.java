package com.webflux.webflux.controller;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.webflux.webflux.document.Playlist;
import com.webflux.webflux.services.PlaylistService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@RestController
public class PlaylistController {
    
    @Autowired
    PlaylistService service;

    @GetMapping(value="/playlist")
    public Flux<Playlist> getPlaylist() {
        return service.findAll();
    }

    @GetMapping(value="/playlist/{id}")
    public Mono<Playlist> getPlaylistId(@PathVariable String id) {
        return service.findById(id);
    }

    @PostMapping(value="/playlist")
    public Mono<Playlist> savePlaylisy(@RequestBody Playlist playlist) {
        return service.save(playlist);
    }

    @GetMapping(value="/playlist/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Tuple2<Long, Playlist>> getPlaylistByEvents() {
        Flux<Long> interval = Flux.interval(Duration.ofSeconds(5));
        Flux<Playlist> events = service.findAll();
        System.out.println("Passou aqui events.");
        return Flux.zip(interval, events);
    }

    @GetMapping(value="/playlist/mvc")
	public List<String> getPlaylistByMvc() throws InterruptedException {

		System.out.println("---Start get Playlists by MVC--- " + LocalDateTime.now());


		List<String> playlistList = new ArrayList<>();
		playlistList.add("Java 8");
		playlistList.add("Spring Security");
		playlistList.add("Github");
		playlistList.add("Deploy de uma aplicação java no IBM Cloud");
		playlistList.add("Bean no Spring Framework");
		TimeUnit.SECONDS.sleep(15);

		return playlistList;

	}
}
