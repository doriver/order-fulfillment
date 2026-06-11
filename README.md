# 주문 fulfillment 시스템

판매처에서 발생한 결제 완료된 주문이, Order Management System과 풀필먼트를 통해 실제로 이행(고객에게 배송)되기 위해 필요한 시스템
* 3개의 시스템(oms, tms, wms)이 사용됨, oms와 Transportation Management System
에 포커스를 둠
* 일단, was 1개 에서 구현하고 나서   
다음으로, 각 시스템별로 was 따로 만들어서 구현(was 3개, 시스템간 통신 필요)
  * 기술스택 : SpringBoot, MySQL
  * 외부 연동없이, DB안에서 모든 흐름이 닫히도록 설계

<details>
<summary><h2> ✅ 소프트웨어 아키텍처</h2></summary>
ddd 이미지(height="300") + 관련 설명 조금 + 패키지 구조

<h3>Rest API 응답 설계</h3>

'HTTP 상태코드' 에 따른 응답
* 2xx은 @controller에서
* 4xx, 5xx 은 @ExceptionHandler 쪽에서
  * 커스텀한 Expected4xxException, Expected5xxException를 api로직에서 throw함
* 응답형식은 ApiResponse클래스로 일괄 처리
  * 정적 팩토리 메서드(Static Factory Method)패턴을 사용


<h3>DB 관련 정책</h3>

(예외상황 있을수 있음)
* FK 사용x , 참조필드에 index를 사용한다.
* JPA를 기본으로 하되    
  동적쿼리, 복잡한 쿼리등은 MyBatis를 이용한다.
* JPA 연관관계는 @ManyToOne(fetch = FetchType.LAZY) 만 사용한다.

</details>

<details>
<summary><h2> ✅ 데이터와 ERD</h2></summary>
약식 erd 메인으로 하나 놓고
  <details>
  <summary>Order Management System</summary>

  </details>

  <details>
  <summary>Transportation Management System</summary>

  </details>
</details>

<details>
<summary><h2> 🕸️ 시스템의 기반과, 상황에 대한 가정 </h2></summary>

  <div>
    <ul>
      <li> 배송 (회차까지 고려된)코스에 주문이 싸이는 구조
      </li>
      <li> 출고는 (창고들이 모여있는)물류 센터 단위로 이루어진다.
      </li>
      <li> 
      </li>
    </ul>
  </div>

</details>

## 🎥 주요 기능들

<details>
<summary><h3> 판매처의 결제완료된 주문 oms에서 받기 </h3></summary>
프로세스
  <div>
    <ol>
      <li> 판매처에서 결제 완료까지 된 주문이 oms로 전송된다.
      </li>
      <li> oms에서 이를 받아, oms에 맞는 데이터로 매핑 
      </li>
      <li> oms의 order와 orderItem으로 저장
      </li>
    </ol>
  </div>
특징
  <div>
    <ul>
      <li> 배송권역, sku 매핑 필요
      </li>
    </ul>
  </div>
  <details>
  <summary>데이터 관련</summary>

  </details>
</details>
<details>
<summary><h3> oms에서 wms, tms 와 통신을 통한 출고, 배송의 결정 </h3></summary>
특징
  <div>
    <ul>
      <li> 
      </li>
      <li> 
      </li>
    </ul>
  </div>  
프로세스
  <div>
    <ol>
      <li> 특정 주문의 데이터를 wms, tms에게 동시에 전달
      </li>
      <li> wms, tms에게 받은 응답값을 바탕으로 출고, 배송 결정 
      </li>
      <li> 결정된 출고, 배송을 wms, tms에게 전달하여 이행하도록 함 
      </li>
    </ol>
  </div>

</details>

<details>
<summary><h3> </h3></summary>

</details>


<details>
<summary><h2> 📄 세부 기능들</h2></summary>

</details>