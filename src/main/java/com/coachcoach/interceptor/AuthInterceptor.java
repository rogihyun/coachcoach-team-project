package com.coachcoach.interceptor;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.coachcoach.domain.Coach;
import com.coachcoach.domain.Member;
import com.coachcoach.interceptor.Auth.Role;

// 인터셉터 만들기
// => 프론트 컨트롤러와 페이지 컨트롤러 사이에 코드를 삽입하는 기술
// => 프론트 컨트롤러와 뷰 컴포넌트 사이에 코드를 삽입하는 기술
//
// 인터셉터를 배치하기
// => 프론트 컨트롤러의 IoC 설정 파일에 배치 정보를 추가한다.
//
public class AuthInterceptor extends HandlerInterceptorAdapter {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    // 1. handler 종류 확인
    // 우리가 관심 있는 것은 Controller에 있는 메서드이므로 HandlerMethod 타입인지 체크
    if (handler instanceof HandlerMethod == false) {
      // return true이면 Controller에 있는 메서드가 아니므로, 그대로 컨트롤러로 진행
      return true;
    }

    // 2.형 변환
    HandlerMethod handlerMethod = (HandlerMethod) handler;

    // 3. @Auth 받아오기
    Auth auth = handlerMethod.getMethodAnnotation(Auth.class);
    Auth classAuth = handlerMethod.getMethod().getDeclaringClass().getAnnotation(Auth.class);

    // 4. method 또는 class에 @Auth가 없는 경우, 즉 인증이 필요 없는 요청 => 즉 비회원도 접근 가능한 페이지
    if (auth == null && classAuth == null) {
      return true;
    }

    // 5. @Auth가 있는 경우이므로, 세션이 있는지 체크
    HttpSession session = request.getSession();
    if (session == null) {
      // 로그인 화면으로 이동
      response.sendRedirect(request.getContextPath() + "/app/auth/login");
      return false;
    }

    // 6. 세션이 존재하면 유효한 유저인지 확인
    Object loginUser = session.getAttribute("loginUser");
    if (session.getAttribute("loginUser") == null) {
      response.sendRedirect(request.getContextPath() + "/app/auth/login");
      return false;
    }

    if (auth != null) {
      Role[] authRoll = auth.role();
      List<String> AuthRollList = new ArrayList<>();
      for (Role r : authRoll) {
        AuthRollList.add(r.toString());
      }
      if (loginUser instanceof Member) {
        if (!AuthRollList.contains("MEMBER")) {
          response.sendRedirect(request.getContextPath() + "/app/auth/rejected");
          return false;
        }
      } else if (loginUser instanceof Coach) {
        if (!AuthRollList.contains("COACH")) {
          response.sendRedirect(request.getContextPath() + "/app/auth/rejected");
          return false;
        }
      }
    } else if (classAuth != null) {
      Role[] classAuthRoll = classAuth.role();
      List<String> AuthRollList = new ArrayList<>();
      for (Role r : classAuthRoll) {
        AuthRollList.add(r.toString());
      }
      // @auth 애노테이션이 붙은 메서드에 접근 가능한지 설정하기
      // member일 경우
      if (loginUser instanceof Member) {
        if (!AuthRollList.contains("MEMBER")) {
          response.sendRedirect(request.getContextPath() + "/app/auth/rejected");
          return false;
        }
      } else if (loginUser instanceof Coach) {
        if (!AuthRollList.contains("COACH")) {
          response.sendRedirect(request.getContextPath() + "/app/auth/rejected");
          return false;
        }
      }
    }

    // // 7. admin일 경우
    // String role = auth.role().toString();
    // if ("ADMIN".equals(role)) {
    // // admin임을 알 수 있는 조건을 작성한다.
    // // ex) 서비스의 id가 root이면 admin이다.
    // if ("root".equals(authUser.getId()) == false) { // admin이 아니므로 return false
    // response.sendRedirect(request.getContextPath());
    // return false;
    // }
    // }

    // 8. 접근허가, 즉 메서드를 실행하도록 함
    return true;
  }
}
