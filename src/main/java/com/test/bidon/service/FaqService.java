package com.test.bidon.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.test.bidon.entity.Faq;

@Service
public class FaqService {

	private List<Faq> faqList = new ArrayList<>();

    // FAQ 데이터 초기화
    public FaqService() {
        faqList.add(new Faq(1L, "실시간 경매는 어떻게 참여하나요?", "메인 홈페이지에 카테고리 목록 하단에 '실시간 경매' 리스트를 확인하실 수 있습니다."));
        faqList.add(new Faq(2L, "저의 경매 내역을 확인하고 싶어요.", "저희 사이트 회원이실 경우 마이페이지에서 경매 기록을 확인하실 수 있습니다."));
        faqList.add(new Faq(3L, "경매 마감시간이 궁금합니다.", "페이지 상단에 경매 카테고리를 클릭하시면 남은 시간을 확인할 수 있습니다."));
        faqList.add(new Faq(4L, "입찰 금액을 잘못 적었는데 취소방법이 궁금합니다.", "경우에 따라 입찰을 취소할 수 없으니 신중히 진행하시길 바랍니다."));
        faqList.add(new Faq(5L, "제가 좋아하는 물건이 있는데 경매가 시작되면 알림 기능이 있나요?", "위시리스트에 물품을 추가하면 알림이 전달됩니다."));
    }

    // FAQ 목록 반환
    public List<Faq> getAllFaqs() {
        return faqList;
    }

    // FAQ 수정
    public void updateFaq(Long id, String title, String content) {
        for (Faq faq : faqList) {
            if (faq.getId().equals(id)) {
                faq.setTitle(title);
                faq.setContent(content);
                break;
            }
        }
    }
}
