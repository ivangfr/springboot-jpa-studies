package com.ivanfranchin.jpalocking.star;

import com.ivanfranchin.jpalocking.player.Player;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@Import(StarCollectionService.class)
class StarCollectionServiceTest {

    @MockitoBean
    private StarCollectionRepository starCollectionRepository;

    @Autowired
    private StarCollectionService starCollectionService;

    @Test
    void getAvailableStarCollectionsShouldReturnList() {
        Player player = new Player();
        player.setId(1L);
        List<StarCollection> expected = List.of(new StarCollection());
        when(starCollectionRepository.findByPlayerIdAndNumAvailableGreaterThanOrderByCreatedAt(1L, 0))
                .thenReturn(expected);

        List<StarCollection> result = starCollectionService.getAvailableStarCollections(player);

        assertThat(result).isEqualTo(expected);
    }
}
