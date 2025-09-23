package com.example.matchingmaster.global.command;

import com.example.matchingmaster.domain.creatematch.dto.MatchCreateRequest;
import com.example.matchingmaster.domain.creatematch.entity.MatchSession;
import com.example.matchingmaster.domain.creatematch.entity.MatchSport;
import com.example.matchingmaster.domain.creatematch.service.MatchCreateService;
import com.example.matchingmaster.global.error.CustomException;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;


@Component
@RequiredArgsConstructor
public class MatchCreateMessageHandler extends ListenerAdapter {

    private final MatchCreateService service;

    private static final Pattern P = Pattern.compile(
            "^!(?<sport>(축구|농구|배구|배드민턴|탁구))\\.(?<session>(점심|저녁))\\.(?<size>\\d{1,2})명?$"
    );


    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        if (e.getAuthor().isBot()) return;

        final String raw = e.getMessage().getContentRaw().trim();
        final var m = P.matcher(raw);
        if (!m.matches()) return;

        try {
            MatchSport sport = MatchSport.fromLabel(m.group("sport"));
            MatchSession session = MatchSession.fromLabel(m.group("session"));
            int size = Integer.parseInt(m.group("size"));

            var res = service.create(new MatchCreateRequest(
                    e.getGuild().getId(),
                    e.getChannel().getId(),
                    e.getAuthor().getId(),
                    sport,
                    session,
                    size,
                    e.getMessage().getId()
            ));

            e.getMessage().reply("경기 생성 완료: #" + res.id()
                            + "\n종목: " + res.sport()
                            + " | 구간: " + (res.session() == MatchSession.LUNCH ? "점심" : "저녁")
                            + " | 정원: " + res.maxSize() + "명 | 상태: OPEN")
                    .queue();

        } catch (IllegalArgumentException ex) {
            e.getMessage().reply("입력 형식/값을 확인해주세요. (예: `!축구.점심.10명`) "
                    + "종목 가능: 축구/농구/배구/배드민턴/탁구, 구간: 점심|저녁, 인원: 2~30명").queue();

        } catch (CustomException ex) {
            e.getMessage().reply(ex.getErrorCode().getMessage()).queue();

        } catch (Exception ex) {
            e.getMessage().reply("알 수 없는 오류: " + ex.getClass().getSimpleName()).queue();
        }
    }
}

