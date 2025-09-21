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
            "^!(?<sport>[가-힣]{1,10})\\.(?<session>점심|저녁)\\.(?<size>\\d{1,2})명?$"
    );

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        if (e.getAuthor().isBot()) return;
        var msg = e.getMessage().getContentRaw().trim();
        var m = P.matcher(msg);
        if (!m.matches()) return;

        try {
            MatchSport sport = MatchSport.valueOf(m.group("sport"));
            MatchSession session = "점심".equals(m.group("session"))
                    ? MatchSession.LUNCH : MatchSession.DINNER;
            int size = Integer.parseInt(m.group("size"));

            var res = service.create(new MatchCreateRequest(
                    e.getGuild().getId(),
                    e.getChannel().getId(),
                    e.getAuthor().getId(),
                    sport,
                    session,
                    size,
                    e.getMessageId()
            ));

            e.getMessage().reply("경기 생성 완료: #" + res.id()
                    + "\n종목: " + res.sport()
                    + " | 구간: " + (res.session() == MatchSession.LUNCH ? "점심" : "저녁")
                    + " | 정원: " + res.maxSize() + "명 | 상태: OPEN"
            ).queue();

        } catch (IllegalArgumentException ex) {
            // enum valueOf 실패 시
            e.getMessage().reply("지원하지 않는 종목입니다. (가능: 축구, 농구, 배구, 배드민턴, 탁구)").queue();

        } catch (CustomException ex) {
            // 서비스 계층에서 던진 명확한 예외
            e.getMessage().reply(ex.getErrorCode().getMessage()).queue();

        } catch (Exception ex) {
            e.getMessage().reply("알 수 없는 오류 발생: " + ex.getClass().getSimpleName()).queue();
        }
    }
}
