package kr.sshsoft.control;

import java.util.List;
import java.util.Scanner;

import kr.sshsoft.dao.ProductDAO;
import kr.sshsoft.vo.ProductVO;

public class ProductControl {
	Scanner sc = new Scanner(System.in); // 스캐너
	ProductDAO pdao = new ProductDAO(); // 상품관리 DAO

	public void run() {
		boolean isTrue = true;

		while (isTrue) {
			System.out.println();
			System.out.println("상품관리 프로그램 v 1.0\n");
			System.out.println("====================================");
			System.out.println("1. 상품 관리 2. 상품 입출고 관리 3. 종료");
			System.out.println("====================================");
			System.out.print("선택 > ");
			int selectMenu = Integer.parseInt(sc.nextLine());

			// 메뉴 선택 시 스위치문으로 반복
			switch (selectMenu) {
			case 1:
				// 상품관리
				selManager();
				break;
			case 2:
				invenManager();
				break;
			case 3:
				System.out.println("프로그램을 종료합니다!");
				isTrue = false;
				break;
			default:
				System.out.println("잘못된 메뉴를 선택하였습니다. 다시 선택해주세요.");
				break;
			}
		}

	}// end run

	// 상품관리
	void selManager() {
		boolean isTrue = true;

		while (isTrue) {
			System.out.println();
			System.out.println("\t상품관리 세부 메뉴");
			System.out.println("\t-------------------------------------------------------------------------------");
			System.out.println("\t1. 상품 목록 2. 상품 상세보기 3. 상품 등록 4. 상품 수정 5. 상품 삭제 6. 상품 통계 7. 처음으로");
			System.out.println("\t-------------------------------------------------------------------------------");
			System.out.print("\t선택 > ");
			int selectMenu = Integer.parseInt(sc.nextLine());

			// 메뉴 선택 시 스위치문으로 반복
			switch (selectMenu) {
			case 1:
				// 상품 목록
				selProduct();
				break;
			case 2:
				// 상품 상세보기
				viewProduct();
				break;
			case 3:
				// 상품 등록
				regProduct();
				break;
			case 4:
				// 상품 수정
				modProduct();
				break;
			case 5:
				// 상품 삭제
				delProduct();
				break;
			case 6:
				// 상품 통계
				statProduct();
				break;
			case 7:
				System.out.println("\t처음으로 돌아갑니다!");
				isTrue = false;
				break;
			default:
				System.out.println("\t잘못된 메뉴를 선택하였습니다. 다시 선택해주세요.");
				break;
			}
		}

	}// end selManager

	// 상품 목록
	void selProduct() {
		System.out.println();
		List<ProductVO> products = pdao.selectList();
		System.out.println("\t상품번호 \t 상품명  \t\t\t 상품가격 \t\t\t  재고");
		System.out.println("\t---------------------------------------------------------------");
		for (ProductVO pvo : products) {
			System.out.println(pvo.briefShow());
		}
		System.out.println();
	}// end selProduct

	// 상품 등록
	void regProduct() {
		String pno;
		while (true) {
			System.out.print("\t등록할 상품번호 입력 > ");
			pno = sc.nextLine(); // 로컬변수
			if (pdao.selectExists(pno) == 1) {
				System.out.println("\t이미 등록된 상품번호 입니다. 다시 입력하세요.");
			} else {
				break;
			}
		}

		System.out.print("\t등록할 상품명 입력 > ");
		String pname = sc.nextLine();

		String pprice;
		int priceChkCnt = 0;
		while (true) {
			System.out.print("\t등록할 상품가격 입력 > ");
			pprice = sc.nextLine();
			if (priceChkCnt < 2) {
				if (Integer.parseInt(pprice) <= 0) {
					System.out.println("\t상품 가격이 0원 이하입니다. 다시 입력해주세요.");
					priceChkCnt++;
				} else {
					break;
				}
			} else {
				pprice = "0";
				System.out.println("\t입력 횟수가 2회가 지나서 0원으로 처리됩니다.");
				break;
			}
		}

		System.out.print("\t등록할 상품설명 입력 > ");
		String pmemo = sc.nextLine();

		ProductVO pd = new ProductVO();
		pd.setPdNo(pno);
		pd.setPdName(pname);
		pd.setPdPrice(pprice);
		pd.setMemo(pmemo);

		// 입력기능 호출
		if (pdao.insertProduct(pd)) {
			System.out.println("\t상품 등록 완료!");
		} else {
			System.out.println("\t등록 중 예외 발생!");
		}

	}// end regProduct

	// 상품 정보 수정
	void modProduct() {
		String pno;
		while (true) {
			System.out.print("\t수정할 상품번호 입력 > ");
			pno = sc.nextLine(); // 로컬변수
			if (pdao.selectExists(pno) == 0) {
				System.out.println("\t등록되지 않은 상품번호 입니다. 다시 입력하세요.");
			} else {
				break;
			}
		}

		System.out.print("\t수정할 상품명 입력 > ");
		String pname = sc.nextLine();

		System.out.print("\t수정할 설명 입력 > ");
		String pmemo = sc.nextLine();

		String pprice;
		int priceChkCnt = 0;
		while (true) {
			System.out.print("\t수정할 상품가격 입력 > ");
			pprice = sc.nextLine();
			if (priceChkCnt < 2) {
				if (Integer.parseInt(pprice) <= 0) {
					System.out.println("\t입력한 상품 가격이 0원 이하입니다. 다시 입력해주세요.");
					priceChkCnt++;
				} else {
					break;
				}
			} else {
				pprice = "0";
				System.out.println("\t입력 횟수가 2회가 지나서 0원으로 처리됩니다.");
				break;
			}
		}

		ProductVO pd = new ProductVO();
		pd.setPdNo(pno);
		pd.setPdName(pname);
		pd.setMemo(pmemo);
		pd.setPdPrice(pprice);

		// 입력기능 호출
		if (pdao.updateProduct(pd)) {
			System.out.println("\t상품 수정 완료!");
		} else {
			System.out.println("\t수정 중 오류 발생!");
		}
	}// end modProduct

	// 상품 삭제
	void delProduct() {
		String pno;
		while (true) {
			System.out.print("\t삭제할 상품번호 입력 > ");
			pno = sc.nextLine(); // 로컬변수
			if (pdao.selectExists(pno) == 0) {
				System.out.println("\t등록되지 않은 상품번호 입니다. 다시 입력하세요.");
			} else {
				break;
			}
		}

		ProductVO pvo = new ProductVO();
		pvo.setPdNo(pno);

		// 삭제기능 호출
		if (pdao.deleteProduct(pvo)) {
			System.out.println("\t삭제 완료!");
		} else {
			System.out.println("\t처리중 예외발생!");
		}

	}// end delProduct

	// 상품 상세보기
	void viewProduct() {
		String pno;
		while (true) {
			System.out.print("\t조회할 상품번호 입력 > ");
			pno = sc.nextLine(); // 로컬변수
			if (pdao.selectExists(pno) == 0) {
				System.out.println("\t등록되지 않은 상품번호 입니다. 다시 입력하세요.");
			} else {
				break;
			}
		}

		ProductVO pvo = new ProductVO();
		pvo.setPdNo(pno);
		System.out.println();
		System.out.println("\t상세설명");
		System.out.println("\t--------------------");
		for (ProductVO ppvo : pdao.productView(pvo)) {
			System.out.printf("\t[상품번호] : %s\n", ppvo.getPdNo());
			System.out.printf("\t[상품이름] : %s\n", ppvo.getPdName());
			System.out.printf("\t[상품가격] : %s원\n", ppvo.getPdPrice());
			System.out.printf("\t[상품설명] : %s\n", ppvo.getMemo());
			System.out.printf("\t[재고수량] : %s개\n", pdao.selectProductInven(pno));
			System.out.printf("\t[상품등록일] : %s\n", ppvo.getCreationDate());
		}
		System.out.println();

	}// end viewProduct

	// 상품 통계
	void statProduct() {
		System.out.println();
		System.out.println("\t상품통계");
		System.out.println("\t--------------------");
		for (ProductVO pvo : pdao.statProduct()) {
			System.out.printf("\t[총 상품 수] : %s개\n", pvo.getTotal());
			System.out.printf("\t[최고 가격] : %s원\n", pvo.getMaxPrice());
			System.out.printf("\t[최저 가격] : %s원\n", pvo.getMinPrice());
			System.out.printf("\t[평균 가격] : %s원\n", pvo.getAvgPrice());
			System.out.printf("\t[총 재고 수] : %s개\n", pvo.getPdTotalCnt());
		}
		System.out.println();
	}// end statProduct

	// 상품 입출고 관리
	void invenManager() {
		boolean isTrue = true;

		while (isTrue) {
			System.out.println();
			System.out.println("\t입출고관리 세부 메뉴");
			System.out.println("\t----------------------------------------------------------------------");
			System.out.println("\t1. 입고하기 2. 출고하기 3. 입출고내역보기(최근10건) 4. 상품별 입출고 통계 5. 처음으로");
			System.out.println("\t----------------------------------------------------------------------");
			System.out.print("\t선택 > ");
			int selectMenu = Integer.parseInt(sc.nextLine());

			// 메뉴 선택 시 스위치문으로 반복
			switch (selectMenu) {
			case 1:
				// 입고하기
				intPutProduct();
				break;
			case 2:
				// 출고하기
				outPutProduct();
				break;
			case 3:
				// 최근입출고내역보기
				putList();
				break;
			case 4:
				// 상품 입출고 통계
				putStat();
				break;
			case 5:
				System.out.println("\t처음으로 돌아갑니다!");
				isTrue = false;
				break;
			default:
				System.out.println("\t잘못된 메뉴를 선택하였습니다. 다시 선택해주세요.");
				break;
			}
		}

	}// end invenManager

	// 상품 입고
	void intPutProduct() {
		String pno;
		while (true) {
			System.out.print("\t입고할 상품번호 입력 > ");
			pno = sc.nextLine(); // 로컬변수
			if (pdao.selectExists(pno) == 0) {
				System.out.println("\t등록되지 않은 상품번호 입니다. 다시 입력하세요.");
			} else {
				break;
			}
		}

		String piCnt;
		int piChkCnt = 0;
		while (true) {
			System.out.print("\t입고수량 입력 > ");
			piCnt = sc.nextLine();
			if (piChkCnt < 2) {
				if (Integer.parseInt(piCnt) <= 0) {
					System.out.println("\t입력한 입고 수량이 0개 이하입니다. 다시 입력해주세요.");
					piChkCnt++;
				} else {
					break;
				}
			} else {
				System.out.println("\t입력 횟수가 2회가 지나서 처음으로 되돌아갑니다.");
				invenManager();
				break;
			}
		}

		ProductVO pvo = new ProductVO();
		pvo.setPdNo(pno);
		pvo.setPiCnt(piCnt);

		// 입고기능 호출
		if (pdao.updateInPutProduct(pvo)) {
			System.out.println("\t입고 완료!");
		} else {
			System.out.println("\t처리중 예외발생!");
		}

	}// end intPutProduct

	// 상품 출고
	void outPutProduct() {
		String pno;
		while (true) {
			System.out.print("\t출고할 상품번호 입력 > ");
			pno = sc.nextLine(); // 로컬변수
			if (pdao.selectExists(pno) == 0) {
				System.out.println("\t등록되지 않은 상품번호 입니다. 다시 입력하세요.");
			} else {
				break;
			}
		}

		String piCnt;
		int piChkCnt = 0;
		while (true) {
			System.out.print("\t출고수량 입력 > ");
			piCnt = sc.nextLine();
			if (piChkCnt < 2) {
				if (Integer.parseInt(piCnt) <= 0) {
					System.out.println("\t입력한 출고 수량이 0개 이하입니다. 다시 입력해주세요.");
					piChkCnt++;
				} else {
					if (Integer.parseInt(piCnt) < pdao.selectProductInven(pno)) {
						break;
					} else {
						System.out.println(
								"남은 재고보다 출고 수량이 많습니다. 현재 출고 가능한 재고는 " + pdao.selectProductInven(pno) + "개 입니다.");
						piChkCnt++;
					}
				}
			} else {
				piCnt = "0";
				System.out.println("\t입력 횟수가 2회가 지나서 처음으로 되돌아갑니다.");
				break;
			}
		}

		ProductVO pvo = new ProductVO();
		pvo.setPdNo(pno);
		pvo.setPiCnt(piCnt);

		// 출고기능 호출
		if (pdao.updateOutPutProduct(pvo)) {
			System.out.println("\t출고 완료!");
		} else {
			System.out.println("\t처리중 예외발생!");
		}

	}// end outPutProduct

	// 입출고내역
	void putList() {
		System.out.println();
		List<ProductVO> products = pdao.selectPutList();
		System.out.println("\t상품명   \t\t\t 재고타입 \t\t\t 재고수 \t\t 처리일");
		System.out.println("\t---------------------------------------------------------------------------");
		for (ProductVO pvo : products) {
			System.out.println(pvo.briefPutShow());
		}
		System.out.println();

	}// end putList

	// 입출고통계
	void putStat() {
		System.out.println();
		System.out.println("\t입출고 통계");
		System.out.println("\t--------------------");
		for (ProductVO pvo : pdao.statInvenProduct()) {
			System.out.printf("\t[총 입출고 건수] : %s건\n", pvo.getPiTotCnt());
			System.out.printf("\t[총 입고 수량] : %s개\n", pvo.getInCnt());
			System.out.printf("\t[총 출고 수량] : %s개\n", pvo.getOutCnt());
			System.out.printf("\t[현재 총 재고] : %s개\n", pvo.getPdInven());
		}
		System.out.println();

	}// end putStat

}// end class
