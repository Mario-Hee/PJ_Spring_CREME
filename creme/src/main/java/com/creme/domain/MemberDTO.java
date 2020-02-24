package com.creme.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MemberDTO {
	private String id;
	private String pw;
	private String name;
	private String phone;
	private String email;
	private String postcode;		// 우편번호
	private String addr1;			// 주소
	private String addr2;			// 상세주소
	private String useon;			// 이용약관 동의 유무
	private String primaryon;		// 개인정보 수집 및 이용 동의 유무
	private String locon;			// 위치정보 이용약관 동의 유무
	private String eventon;			// 이벤트 등 프로모션 알림 메일 수신
	private String useyn;			// ID 사용여부
	private String regdate;			// 수신여부

	
}

