package ch.mtrail.test.stream;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Kids {

	public static Set<String> getKidNames(List<Person> people) {
		Set<String> kids = new HashSet<>();
		for (Person p : people) {
			if (p.getAge() < 18) {
				kids.add(p.getName());
			}
		}
		return kids;
	}

}