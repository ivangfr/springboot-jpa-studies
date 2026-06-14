# TODO

## jpa-locking: PlayerControllerTest improvements

- [ ] **Handle 409 responses explicitly** — Catch `RestClientException` in `redeemStars` (and `collectStars`) so failed concurrent requests don't silently kill threads. Verify the expected 409 status in a controlled way (e.g., separate test cases for concurrent redemption).
- [ ] **Add concurrency coordination** — Use `CyclicBarrier` or `CountDownLatch` to ensure all threads fire requests simultaneously, making the test deterministic.
- [ ] **Assert 409 errors occurred** — Track which threads received 409 responses and assert that exactly one of the two concurrent `redeemStars` per player failed.
- [ ] **Verify intermediate state** — Assert star collection results before proceeding to the redemption phase.
- [ ] **Replace raw `Thread` with `ExecutorService`** — Use `ExecutorService.invokeAll()` or similar for cleaner lifecycle management.
- [ ] **Add `@DisplayName`** to the test method for better reporting.
- [ ] **Separate concurrency concerns** — Consider splitting into focused tests:
  - Happy-path sequential redemption
  - Concurrent collection (no collision expected)
  - Concurrent redemption (exactly one 409 per player)
