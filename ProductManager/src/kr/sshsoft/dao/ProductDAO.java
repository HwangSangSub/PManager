package kr.sshsoft.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.sshsoft.vo.ProductVO;

public class ProductDAO extends DAO {
	// 목록기능
	public List<ProductVO> selectList() {
		String sql = "SELECT pd.pd_no";
		sql += " , pd.pd_name";
		sql += " , pd.pd_price";
		sql += " , (SELECT NVL(SUM(CASE WHEN pi_type = 'IN' THEN  TO_NUMBER(pi_cnt) ELSE (TO_NUMBER(pi_cnt) * -1) END), 0) FROM tbl_product_inven WHERE pd_no = pd.pd_no) AS pd_cnt";
		sql += " FROM tbl_product pd ORDER BY pd.pd_no";
		List<ProductVO> list = new ArrayList<>();
		conn = getConn();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				ProductVO pvo = new ProductVO();

				pvo.setPdNo(rs.getString("pd_no"));
				pvo.setPdName(rs.getString("pd_name"));
				pvo.setPdPrice(rs.getString("pd_price"));
				pvo.setPdCnt(rs.getString("pd_cnt"));

				list.add(pvo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}// end selectList

	// 단건조회.(상품이 있는지 확인하기)
	public int selectExists(String pno) {
		String sql = "SELECT COUNT(1) FROM tbl_product";
		sql += " WHERE pd_no = ?";
		conn = getConn();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, pno);
			rs = psmt.executeQuery();
			while (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}// end selectExists

	// 등록기능.
	public boolean insertProduct(ProductVO pvo) {
		String sql = "INSERT INTO tbl_product(pd_no, pd_name, pd_price, memo)";
		sql += "VALUES (?, ?, ?, ?)";
		conn = getConn();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, pvo.getPdNo());
			psmt.setString(2, pvo.getPdName());
			psmt.setString(3, pvo.getPdPrice());
			psmt.setString(4, pvo.getMemo());
			int r = psmt.executeUpdate();
			if (r == 1) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}// end insertProduct

	// 상품 수정
	public boolean updateProduct(ProductVO pvo) {
		String sql = "UPDATE tbl_product ";
		sql += "SET pd_name = NVL(?, pd_name) ";
		sql += ", pd_price = NVL(?, pd_price) ";
		sql += ", memo = NVL(?, memo) ";
		sql += "WHERE pd_no = ?";
		conn = getConn();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, pvo.getPdName());
			psmt.setString(2, pvo.getPdPrice());
			psmt.setString(3, pvo.getMemo());
			psmt.setString(4, pvo.getPdNo());
			int r = psmt.executeUpdate();
			if (r == 1) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}

	// 상품삭제
	public boolean deleteProduct(ProductVO pvo) {
		String sql = "DELETE FROM tbl_product ";
		sql += "WHERE pd_no = ?";
		conn = getConn();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, pvo.getPdNo());
			int r = psmt.executeUpdate();
			if (r == 1) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}// end deleteProduct

	// 상품상세보기
	public List<ProductVO> productView(ProductVO pvo) {
		String sql = "SELECT pd_no";
		sql += ", pd_name";
		sql += ", NVL(pd_price, '0') AS pd_price ";
		sql += ", NVL(memo, '-') AS memo ";
		sql += ", creation_date ";
		sql += " FROM tbl_product";
		sql += " WHERE pd_no = ?";
		List<ProductVO> list = new ArrayList<>();
		conn = getConn();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, pvo.getPdNo());
			rs = psmt.executeQuery();
			while (rs.next()) {
				ProductVO selPvo = new ProductVO();

				selPvo.setPdNo(rs.getString("pd_no"));
				selPvo.setPdName(rs.getString("pd_name"));
				selPvo.setPdPrice(rs.getString("pd_price"));
				selPvo.setMemo(rs.getString("memo"));
				selPvo.setCreationDate(rs.getDate("creation_date"));

				list.add(selPvo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}// end productView

	// 상품통계
	public List<ProductVO> statProduct() {
		String sql = "SELECT COUNT(pd.pd_no) AS total";
		sql += " , NVL(MAX(pd.pd_price), 0) AS max_price";
		sql += " , NVL(MIN(pd.pd_price), 0) AS min_price";
		sql += " , NVL(ROUND(AVG(pd.pd_price)), 0) AS avg_price";
		sql += " FROM tbl_product pd";
		List<ProductVO> list = new ArrayList<>();
		conn = getConn();
		try {
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			while (rs.next()) {
				ProductVO pvo = new ProductVO();

				pvo.setTotal(rs.getString("total"));
				pvo.setMaxPrice(rs.getString("max_price"));
				pvo.setMinPrice(rs.getString("min_price"));
				pvo.setAvgPrice(rs.getString("avg_price"));
				pvo.setPdTotalCnt(Integer.toString(selectAllProductInven()));

				list.add(pvo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}// end statProduct

	// 전체 재고 수 확인
	public int selectAllProductInven() {
		String sql = "SELECT NVL(SUM(CASE WHEN pi_type = 'IN' THEN  TO_NUMBER(pi_cnt) ELSE TO_NUMBER((pi_cnt * -1))END), 0) as pd_inven ";
		sql += "FROM tbl_product_inven ";
		conn = getConn();
		try {
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			while (rs.next()) {
				return Integer.parseInt(rs.getString("pd_inven"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	// 재고 수 확인
	public int selectProductInven(String pdno) {
		String sql = "SELECT NVL(SUM(CASE WHEN pi_type = 'IN' THEN  TO_NUMBER(pi_cnt) ELSE (TO_NUMBER(pi_cnt) * -1) END), 0) as pd_inven ";
		sql += "FROM tbl_product_inven ";
		sql += "WHERE pd_no = ?";
		conn = getConn();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, pdno);
			rs = psmt.executeQuery();
			while (rs.next()) {
				return Integer.parseInt(rs.getString("pd_inven"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;

	}

	
	// 입고등록
	public boolean updateInPutProduct(ProductVO pvo) {
		
		
		String sql = "INSERT INTO tbl_product_inven(pi_no, pd_no, pi_type, pi_cnt)";
		sql += "VALUES ((SELECT NVL(MAX(TO_NUMBER(pi_no)), 0) FROM tbl_product_inven), ?, 'IN', ?)";
		conn = getConn();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, pvo.getPdNo());
			psmt.setString(2, pvo.getPiCnt());
			int r = psmt.executeUpdate();
			if (r == 1) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	// 출고등록
	public boolean updateOutPutProduct(ProductVO pvo) {
		String sql = "INSERT INTO tbl_product_inven(pi_no, pd_no, pi_type, pi_cnt)";
		sql += "VALUES ((SELECT NVL(MAX(TO_NUMBER(pi_no)), 0) + 1 FROM tbl_product_inven), ?, 'OUT', ?)";
		conn = getConn();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, pvo.getPdNo());
			psmt.setString(2, pvo.getPiCnt());
			int r = psmt.executeUpdate();
			if (r == 1) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	// 입출고내역
	public List<ProductVO> selectPutList() {
		String sql = "SELECT";
		sql += " (SELECT pd_name FROM tbl_product WHERE pd_no = pi.pd_no) as pd_name";
		sql += " , pi.pi_type";
		sql += " , pi.pi_cnt";
		sql += " , creation_date";
		sql += " FROM tbl_product_inven pi";
		sql += " WHERE ROWNUM <= 10";
		sql += " ORDER BY TO_NUMBER(pi_no) DESC";
		List<ProductVO> list = new ArrayList<>();
		conn = getConn();
		try {
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			while (rs.next()) {
				ProductVO pvo = new ProductVO();

				pvo.setPdName(rs.getString("pd_name"));
				pvo.setPiType(rs.getString("pi_type"));
				pvo.setPiCnt(rs.getString("pi_cnt"));
				pvo.setCreationDate(rs.getDate("creation_date"));

				list.add(pvo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}// end selectPutList

	// 입출고통계
	public List<ProductVO> statInvenProduct() {
		String sql = "SELECT";
		sql += " COUNT(pi_no) AS pi_tot_cnt";
		sql += " , NVL(SUM(CASE WHEN pi_type = 'IN' THEN TO_NUMBER(pi_cnt) ELSE 0 END), 0) as in_cnt";
		sql += " , NVL(SUM(CASE WHEN pi_type = 'OUT' THEN TO_NUMBER(pi_cnt) ELSE 0 END), 0) as out_cnt";
		sql += " , NVL(SUM(CASE WHEN pi_type = 'IN' THEN  TO_NUMBER(pi_cnt) ELSE TO_NUMBER((pi_cnt * -1))END), 0) as pd_inven";
		sql += " FROM tbl_product_inven";

		List<ProductVO> list = new ArrayList<>();
		conn = getConn();
		try {
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			while (rs.next()) {
				ProductVO pvo = new ProductVO();

				pvo.setPiTotCnt(rs.getString("pi_tot_cnt"));
				pvo.setInCnt(rs.getString("in_cnt"));
				pvo.setOutCnt(rs.getString("out_cnt"));
				pvo.setPdInven(rs.getString("pd_inven"));

				list.add(pvo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}// end statInverProduct

}// end class
