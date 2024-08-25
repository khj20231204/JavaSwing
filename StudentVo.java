package ui_swing;

public class StudentVo {

	String sno;
	String sname;
	int syear;
	String gender;
	String major;
	int score;
	
	public StudentVo(String sno, String sname, int syear, String gender, String major, int score) {
		super();
		this.sno = sno;
		this.sname = sname;
		this.syear = syear;
		this.gender = gender;
		this.major = major;
		this.score = score;
	}
	/**
	 * @return the sno
	 */
	public String getSno() {
		return sno;
	}
	/**
	 * @param sno the sno to set
	 */
	public void setSno(String sno) {
		this.sno = sno;
	}
	/**
	 * @return the sname
	 */
	public String getSname() {
		return sname;
	}
	/**
	 * @param sname the sname to set
	 */
	public void setSname(String sname) {
		this.sname = sname;
	}
	/**
	 * @return the syear
	 */
	public int getSyear() {
		return syear;
	}
	/**
	 * @param syear the syear to set
	 */
	public void setSyear(int syear) {
		this.syear = syear;
	}
	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}
	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}
	/**
	 * @return the major
	 */
	public String getMajor() {
		return major;
	}
	/**
	 * @param major the major to set
	 */
	public void setMajor(String major) {
		this.major = major;
	}
	/**
	 * @return the score
	 */
	public int getScore() {
		return score;
	}
	/**
	 * @param score the score to set
	 */
	public void setScore(int score) {
		this.score = score;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "VariationClass [sno=" + sno + ", sname=" + sname + ", syear=" + syear + ", gender=" + gender
				+ ", major=" + major + ", score=" + score + "]";
	}
}
