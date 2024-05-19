resource "kubectl_manifest" "is-my-burguer-controle-pedido-deployment" {
  depends_on = [
    data.aws_eks_cluster.cluster,
    kubernetes_secret.is-my-burguer-controle-pedido-db
  ]
  yaml_body = <<YAML
apiVersion: apps/v1
kind: Deployment
metadata:
  name: is-my-burguer-controle-pedido
  namespace: is-my-burguer
  labels:
    name: is-my-burguer-controle-pedido
    app: is-my-burguer-controle-pedido
spec:
  replicas: 1
  selector:
    matchLabels:
      app: is-my-burguer-controle-pedido
  strategy:
    type: Recreate
  template:
    metadata:
      labels:
        app: is-my-burguer-controle-pedido
    spec:
      containers:
        - name: is-my-burguer-controle-pedido
          resources:
            limits:
              cpu: "1"
              memory: "300Mi"
            requests:
              cpu: "300m"
              memory: "300Mi"
          env:
            - name: MONGODB_PASSWORD
              valueFrom:
                secretKeyRef:
                  name: is-my-burguer-controle-pedido-db
                  key: password
            - name: MONGODB_USER
              valueFrom:
                secretKeyRef:
                  name: is-my-burguer-controle-pedido-db
                  key: username
            - name: MONGODB_HOST
              valueFrom:
                secretKeyRef:
                  name: is-my-burguer-controle-pedido-db
                  key: host
            - name: CLIENT_DOMAIN
              valueFrom:
                secretKeyRef:
                  name: is-my-burguer-cognito
                  key: cognito_domain
            - name: AWS_COGNITO_USER_POOL_ID
              valueFrom:
                secretKeyRef:
                  name: is-my-burguer-cognito
                  key: user-pool-id
            - name: AWS_REGION
              value: ${local.region}
            - name: SERVICE_DISCOVERY_USERNAME
              valueFrom:
                secretKeyRef:
                  name: is-my-burguer-sd
                  key: username
            - name: SERVICE_DISCOVERY_PASSWORD
              valueFrom:
                secretKeyRef:
                  name: is-my-burguer-sd
                  key: password
          image: docker.io/ismaelgcosta/is-my-burguer-controle-pedido:${var.TF_VAR_IMAGE_VERSION}
          ports:
            - containerPort: 8543
      restartPolicy: Always
status: {}
YAML
}


resource "kubectl_manifest" "is-my-burguer-controle-pedido-svc" {
  depends_on = [
    data.aws_eks_cluster.cluster,
    kubectl_manifest.is-my-burguer-controle-pedido-deployment
  ]
  yaml_body = <<YAML
apiVersion: v1
kind: Service
metadata:
  name: is-my-burguer-controle-pedido-svc
  namespace: is-my-burguer
spec:
  selector:
    app: is-my-burguer-controle-pedido
  ports:
    - name: https
      protocol: TCP
      port: 8543
      targetPort: 8543
YAML
}

resource "kubectl_manifest" "is-my-burguer-controle-pedido-hpa" {
  depends_on = [
    data.aws_eks_cluster.cluster,
    kubectl_manifest.is-my-burguer-controle-pedido-deployment,
    kubectl_manifest.is-my-burguer-controle-pedido-svc
  ]
  yaml_body = <<YAML
apiVersion: autoscaling/v2
kind: HorizontalPodAutoscaler
metadata:
  name: is-my-burguer-controle-pedido-hpa
  namespace: is-my-burguer
spec:
  scaleTargetRef:
    apiVersion: apps/v1
    kind: Deployment
    name: is-my-burguer-controle-pedido
    namespace: is-my-burguer
  minReplicas: 2
  maxReplicas: 2
  behavior:
    scaleDown:
      stabilizationWindowSeconds: 0 # para forçar o kubernets a zerar a janela de tempo e escalar imediatamente
    scaleUp:
      stabilizationWindowSeconds: 0 # para forçar o kubernets a zerar a janela de tempo e escalar imediatamente
  metrics:
  - type: Resource
    resource:
      name: cpu
      target:
        type: Utilization
        averageUtilization: 1 # para forçar o kubernets escalar com 1% de cpu
status:
  observedGeneration: 0
  lastScaleTime:
  currentReplicas: 2
  desiredReplicas: 2
  currentMetrics:
  - type: Resource
    resource:
      name: cpu
YAML
}


resource "kubernetes_secret" "is-my-burguer-controle-pedido-db" {
  metadata {
    name      = "is-my-burguer-controle-pedido-db"
    namespace = "is-my-burguer"
  }

  immutable = false

  data = {
    host = "${data.terraform_remote_state.is-my-burguer-db.outputs.mongodb_endpoint_host}",
    username = "${var.TF_VAR_MONGODB_CONTROLE_PEDIDO_USERNAME}",
    password = "${var.TF_VAR_MONGODB_CONTROLE_PEDIDO_PASSWORD}"
  }

  type = "kubernetes.io/basic-auth"

}


