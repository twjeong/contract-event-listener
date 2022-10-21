# 블록체인 컨트렉트 이벤트 리스너
> 컨트렉트에서 발생하는 이벤트가 수신되면 Amazon SQS에 적재하여 NFT 메인 서비스에 전달
- 스마트 컨트렉트를 java로 래핑(web3j 사용)
- java flowable 객체를 사용하여 web3j event 구독
- 서비스 중단 중에 블록체인 트렌젝션이 발생했을 때를 대비해 마지막 수신된 블럭번호를 SQS attribute에 저장하여 동기화
- NFT 메인 서비스에서 큐처리 중 장애 발생 시 SQS dead-letter Queue를 운영하여 복구
