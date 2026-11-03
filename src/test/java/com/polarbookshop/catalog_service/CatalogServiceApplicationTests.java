package com.polarbookshop.catalog_service;

import com.polarbookshop.catalog_service.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;


import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class CatalogServiceApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@Test
	void whenPostRequestThenBookCreated() {
		var expected = new Book("1231231231", "Title", "Author", 9.90);

		webTestClient
				.post().uri("/books")
				.bodyValue(expected)
				.exchange()
				.expectStatus().isCreated()
				.expectBody(Book.class).value(actual -> {
					assertThat(actual).isNotNull();
					assertThat(actual.isbn()).isEqualTo(expected.isbn());
				});
	}
	@Test
	void whenPutNewBookThenCreated() {
		var book = new Book("1111111111", "Title", "Author", 9.90);

		webTestClient
				.put().uri("/books/" + book.isbn())
				.bodyValue(book)
				.exchange()
				.expectStatus().isCreated();
	}

	@Test
	void whenPutExistingBookThenOk() {
		var book = new Book("2222222222", "Title", "Author", 9.90);

		webTestClient
				.post().uri("/books")
				.bodyValue(book)
				.exchange()
				.expectStatus().isCreated();

		var changed = new Book(book.isbn(), "New Title", "Author", 12.50);

		webTestClient
				.put().uri("/books/" + book.isbn())
				.bodyValue(changed)
				.exchange()
				.expectStatus().isOk()
				.expectBody(Book.class).value(actual -> {
					assertThat(actual.title()).isEqualTo("New Title");
					assertThat(actual.price()).isEqualTo(12.50);
				});
	}

	@Test
	void whenFullLifecycleThenBookIsGoneAtTheEnd() {
		var isbn = "3333333333";
		var book = new Book(isbn, "Title", "Author", 9.90);

		webTestClient
				.post().uri("/books")
				.bodyValue(book)
				.exchange()
				.expectStatus().isCreated();

		webTestClient
				.get().uri("/books/" + isbn)
				.exchange()
				.expectStatus().isOk()
				.expectBody(Book.class).value(actual ->
						assertThat(actual).isEqualTo(book));

		var changed = new Book(isbn, "Updated Title", "Author", 19.90);
		webTestClient
				.put().uri("/books/" + isbn)
				.bodyValue(changed)
				.exchange()
				.expectStatus().isOk()
				.expectBody(Book.class).value(actual ->
						assertThat(actual.title()).isEqualTo("Updated Title"));

		webTestClient
				.delete().uri("/books/" + isbn)
				.exchange()
				.expectStatus().isNoContent();

		webTestClient
				.get().uri("/books/" + isbn)
				.exchange()
				.expectStatus().isNotFound();
	}
}