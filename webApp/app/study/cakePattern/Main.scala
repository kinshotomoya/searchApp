package study.cakePattern

class Main {
  def main(args: Array[String]): Unit = {
    // searchUseCase自体をインジェクトしている
    val useCase = ComponentRegistry.searchUseCase
    useCase.search
  }
}


// 抽象のコンポーネント
// componentで囲って、それぞれ名前空間を作ってあげる
trait JobRepositoryComponent {
  val jobRepository: JobRepository

  trait JobRepository {
    def search: Unit
  }
}

// 具象のコンポーネント
// componentで囲って、それぞれ名前空間を作ってあげる
trait JobRepositoryComponentImple extends JobRepositoryComponent {
  class JobRepositoryImple extends JobRepository {
    override def search: Unit = println("create user")
  }
}

// クライアントのコンポーネント
// 自分型アノテーションを使って、UserRepositoryへの依存性を宣言する
// JobRepositoryComponentの依存性を宣言しているので、
// JobRepositoryComponentのフィールド値や関数を使える
trait SearchUseCaseComponent { self: JobRepositoryComponent =>
  class SearchUseCase {
    def search = {
      // このjobRepositoryがインジェクトされたい
      self.jobRepository.search
    }
  }
}

// インジェクター（DIコンテナの役割）
// SearchUseCaseComponentは、自分型アノテーションでJobRepositoryComponentを宣言しているので
// JobRepositoryComponentImpleもmixinしてあげないといけない
object ComponentRegistry extends SearchUseCaseComponent with JobRepositoryComponentImple {
  // ここで具象クラスのインスタンスを作成してあげる
  override val jobRepository = new JobRepositoryImple
  // SearchUseCaseComponentをextendsしてるからclass SearchUseCaseのインスタンス作成できる
  val searchUseCase = new SearchUseCase
}


// ゴール
// searchusecaseのなかで、具象クラスを見たくない。インターフェースに依存したい

// メリット
//

// デメリット
// ややこしい
